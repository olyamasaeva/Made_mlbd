
from itertools import groupby
from operator import itemgetter
import sys

def read_mapper_output(file, separator=','):
    for line in file:
        yield line.rstrip().split(separator)

def main(separator=','):
    data = read_mapper_output(sys.stdin, separator=separator)
    sum_ck = 0
    mean = 0
    for line in sys.stdin:
        res = line.split(separator)
        ck = int(res[0])
        mk = float(res[1])
        mean = (sum_ck * mean + mk * ck) / (sum_ck + ck)
        sum_ck += ck
    print(f"{sum_ck},{mean}")

if __name__ == "__main__":
    main()