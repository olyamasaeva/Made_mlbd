import sys
import numpy as np


def main(separator='\n'):
    sum = 0
    sz = 0
    for line in sys.stdin:
        if line != None:
            sum += int(line)
            sz += 1
    mean = sum / sz
    print(f"{sz},{mean}")

if __name__ == "__main__":
    main()