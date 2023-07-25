import sys
import numpy as np

def read_input(file):
    for line in file:
        yield line.split()

def main(separator=' '):
    data_path = "data\AB_NYC_2019.csv"
    file = open(data_path, "r")
    data = [int(x) for x in read_input(file) if x != None]
    mean = np.mean(data)
    std = np.std(data)
    print(f"{len(data)} {mean} {std}")

if __name__ == "__main__":
    main()





