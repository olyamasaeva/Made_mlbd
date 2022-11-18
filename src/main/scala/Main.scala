import breeze.linalg._
import java.nio.file.Paths
import java.io.InputStreamReader
import io.circe.yaml.parser
import scala.io.Source

import com.lambdista.config._

import java.io.ObjectOutputStream
import java.io.FileOutputStream
import java.io.{File, FileInputStream}
import scala.reflect.internal.Mode
import scala.util.Random
import scala.math._
import scala.collection.mutable._

case class Metrics(MSE: Double, RMSE: Double)
case class SplitParams(val_size: Double,random_state: Int)
case class TrainParams(  model_type: String, reg_coef: Double)
case class FeatureParams(  target_col: Int)
case class ModelConfig( input_data_path: String,  metric_path: String,  validation_dataset_path: String, predict_data_path: String,
result_path: String, splitting_params: SplitParams,  train_params: TrainParams,  feature_params: FeatureParams)

object Main{
    def get_config(config_name: String) = {
      val source = Source.fromFile(config_name)
      val lines = try source.mkString finally source.close()
      val config: Result[Config] = Config.from(Paths.get(config_name))
      val fooConfig: Result[ModelConfig] = for {
        conf <- config
        result <- conf.as[ModelConfig] 
      } yield result
      val real_config = fooConfig.merge.asInstanceOf[ModelConfig]
      real_config
  }

 def fit(X:DenseMatrix[Double], y:DenseVector[Double], reg_coef: Double): DenseVector[Double] = {
    val w = (inv(X.t * X  + reg_coef *DenseMatrix.eye[Double](X.cols)) * (X.t)) * y
    return w
 }
 def save_dataset(path: String, dataset: DenseMatrix[Double]){
    csvwrite(file= new File(path), dataset)
 }
 def save_column(path: String, columns: DenseVector[Double]){
    csvwrite(file= new File(path), DenseMatrix(columns))
 }
 def read_dataset(path: String): DenseMatrix[Double] = {
    return csvread(file= new File(path), separator=',', skipLines=1)
 }
 def predict(data: DenseMatrix[Double], weights: DenseVector[Double]): DenseVector[Double] = {
    return data * weights
 }
def calc_metrics(save_path:String, predict: DenseVector[Double], real_predict: DenseVector[Double]){
  val MSE = ((predict - real_predict).dot(predict - real_predict))/predict.length 
  //sqrt behaves strange
 // val RMSE = sqrt((predict - real_predict).dot(predict - real_predict))/predict.length 
  new java.io.PrintWriter(save_path) { write("MSE = " + MSE); close }
}

def predict(predict_config: ModelConfig, model: DenseVector[Double]):DenseVector[Double] = {
    val data = read_dataset(predict_config.predict_data_path)
    val predict_result = predict(data, model)
    save_column(predict_config.result_path + "result_predict.csv", predict_result)
    return predict_result
}

  def train(real_config: ModelConfig): DenseVector[Double] = {
      val data = read_dataset(real_config.input_data_path)
      val split_pos = ((1.0 - real_config.splitting_params.val_size) * data.rows).toInt
      val train = data(0 to split_pos , :: )
      val test = data(split_pos to (data.rows - 1), ::)
      val X_train = train.delete(real_config.feature_params.target_col, Axis._1)
      val y_train =  train(::, real_config.feature_params.target_col)
      val X_test = test.delete(real_config.feature_params.target_col, Axis._1)
      val y_test =  test(::, real_config.feature_params.target_col)
      save_dataset(real_config.validation_dataset_path, X_test)
      val w = fit(X_train, y_train, real_config.train_params.reg_coef)
      val train_result = predict(X_train, w)
      val val_result = predict(X_test, w)
      save_column(real_config.result_path + "val_result.csv", train_result)
      calc_metrics(real_config.metric_path + "train_metrics.txt", train_result, y_train)
      calc_metrics(real_config.metric_path + "val_metrics.txt", val_result, y_test)
      return w;
  }

  def main(args: Array[String]) {
      //val filename = "./configs/train_test_config.conf"
      val filename = args(0)
      val real_config = get_config(filename)
      val w = train(real_config)
      predict(real_config, w)
    }
}