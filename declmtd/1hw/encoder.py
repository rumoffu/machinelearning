#!/usr/bin/env python
## This program runs through the dictionary and writes CNF clauses for each word including 1letter a-z

import os, subprocess
#This script reads in a word dictionary and a puzzle file and outputs a CNF encoding to stdout

def main():
  dict_fn = ''
  puzz_fn = ''
  readDict(dict_fn)
  readPuzzle(puzz_fn)

def readDict(dict_fn):
  dic = open(dict_fn, 'r')
  #read and do work

def readPuzzle(puzz_fn):
  puzz = open(puzz_fn, 'r')
  #read and do work

## Function to call main
if __name__ == '__main__':
  main()
  
