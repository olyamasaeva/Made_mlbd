import sys
import numpy as np


def main(separator='\n'):
    sum = 0
    sum_sq = 0
    sz = 0
    for line in sys.stdin:
        if line != None:
            sum += int(line)
            sum_sq += int(line) * int(line)
            sz += 1
    mean = sum / sz
    std = (sz * mean**2 + sum_sq + 2 * mean * sum)/ (sz - 1)
    print(f"{sz},{mean},{std}")

if __name__ == "__main__":
    main()