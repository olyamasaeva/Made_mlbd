
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
    std = 0
    for line in sys.stdin:
        res = line.split(separator)
        ck, mk, vk = int(res[0]), float(res[1]), float(res[2]) 
        mean = (sum_ck * mean + mk * ck) / (sum_ck + ck)
        std = (sum_ck * std + vk * ck)  / (sum_ck + ck) + sum_ck * ck * ((mk - mean) / (sum_ck + ck)) ** 2 
        sum_ck += ck
    print(f"{sum_ck},{mean},{std}")

if __name__ == "__main__":
    main()