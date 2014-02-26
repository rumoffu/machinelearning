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
  dict_fn = 'microwords'
  puzz_fn = 'micro.puzzle'
  #word_dict = readDict(dict_fn)
  readPuzzle(puzz_fn)
  #encodeDict(puzz_fn, word_dict)

def readDict(dict_fn):
  dic = open(dict_fn, 'r')
  # Add # and all single letters to dictionary.
  word_dict = {}
  word_dict['#'] = []
  atoz = map(chr, range(97, 123)) 
  for let in atoz:
    word_dict['#'].append(let)
    word_dict['#' + let] = []
    word_dict['#' + let].append('#')
    # Since every letter is considered a valid word.

  # Go through dictionary and add the words.
  for word in dic:
    word = word.strip() + '#'
    for i in xrange(len(word)):
      if ('#' + word[0:i]) in word_dict:
        if word[i] not in word_dict['#' + word[0:i]]:
          word_dict['#' + word[0:i]].append(word[i])
      else:
          word_dict['#' + word[0:i]] = []
          word_dict['#' + word[0:i]].append(word[i])
  return word_dict

def encodeDict(puzz_fn, word_dict):
  #encode dictionary entries as cnf
  puzz = open(puzz_fn, 'r')
  size = puzz.readline().strip().split()
  x = int(size[0])
  y = int(size[1])
  for prefix, nextlets in word_dict.iteritems():
    for col in xrange(x):
      for row in xrange(y):
        #for i in xrange(len(prefix)):
        i = 0
        clause = '('
        while i < len(prefix):
          offset = len(prefix) - i
          coloffset = col - offset
          prefixi = prefix[i]
          if coloffset < 0:
            break
          clause = clause + '~{prefixi}_{coloffset}_{row}'.format(**locals())
          i = i + 1
          if (i < len(prefix)):
            clause = clause + ' v '
        if coloffset < 0:
          continue
        clause = clause + ') v ('
        for let in nextlets:
          clause = clause + '{let}_{col}_{row} v '.format(**locals())
        clause = clause[:-3] + ')'#cut off + v that overhangs
        print clause
  #for ans in word_dict['#o']:
    #print ans

def readPuzzle(puzz_fn):
  puzz = open(puzz_fn, 'r')
  size = puzz.readline().strip().split()
  x = int(size[0])
  y = int(size[1])
  grid = [['' for i in xrange(y)] for i in xrange(x)]
  linenum = 0
  for line in puzz.readlines():
    line = line.strip()
    grid[linenum] = []
    for char in line:
      grid[linenum].append(char)
    linenum = linenum + 1
  for line in grid:
    print line
  atoz = map(chr, range(97, 123)) 
  for col in xrange(x):
    for row in xrange(y):
      pclause = ''
      if grid[col][row] == '.':
        pclause = pclause + '('
        for let in atoz:
          pclause = pclause + '{let}_{col}_{row} v '.format(**locals())
        pclause = pclause[:-3] + ')\n &'#cut off + v that overhangs
      else:
        pclause = pclause + '(#_{col}_{row})\n & '.format(**locals())
      print pclause
  #print 'opened %s' % puzz_fn
  # Read and do the work.


## Function to call main
if __name__ == '__main__':
  main()
  
