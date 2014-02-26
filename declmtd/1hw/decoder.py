#!/usr/bin/env python

#######################################
##  Kyle Wong, Tifany Yung
##  kwong23@jhu.edu, tyung1@jhu.edu
##  February 26, 2014
##  Declarative Methods Assignment 1
#######################################


## This program traverses the dictionary and writes CNF clauses for each word, including each letter a-z.
## This script reads in a word dictionary and a puzzle format file, and outputs a CNF encoding to stdout.

import os, subprocess
import sys
def main():
  fn = 'micro'
  fn = os.path.splitext(sys.argv[1])[0]
  print fn
  decode(fn)

def decode(fn):
  puz = open(fn + '.puzzle', 'r')
  ansfn = sys.argv[2]
  #ans = open(fn + '.ans', 'r')
  ans = open(ansfn, 'r')
  print ansfn
  header = puz.readline().strip().split()
  x = int(header[0])
  y = int(header[1])
  grid = [['' for j in range(y)] for i in range(x)]
  for line in ans:
    if line[0] == '-': #not an answer
      continue
    line = line.strip()
    pieces = line.split('_')
    if(grid[int(pieces[2])][int(pieces[1])] != '#'):
      grid[int(pieces[2])][int(pieces[1])] = pieces[0]

  print str(x) + " " + str(y)
  for line in grid:
     # print line
     holdLine = ""
     for item in line:
       holdLine = holdLine + item + " "
     print holdLine

## Function to call main
if __name__ == '__main__':
  main()
  
