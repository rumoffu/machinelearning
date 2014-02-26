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

def main():
  fn = 'micro'
  decode(fn)

def decode(fn):
  puz = open(fn + '.puzzle', 'r')
  ans = open(fn + '.ans', 'r')
  for line in ans:
    if line[0] == '-': #not an answer
      continue
    pieces = line.split('_')
    grid = [[]]
    linenum = 0
    grid[linenum] = []
    grid[int(pieces[2])][int(pieces[1])] = pieces[0]


## Function to call main
if __name__ == '__main__':
  main()
  
