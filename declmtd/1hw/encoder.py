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
  readDict(dict_fn)
  readPuzzle(puzz_fn)

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
      # Print word[0:i].
      # Print 'word[i] %s ' % word[i].
      
 

  for ans in word_dict['#o']:
    print ans

def readPuzzle(puzz_fn):
  puzz = open(puzz_fn, 'r')
  print 'opened %s' % puzz_fn
  # Read and do the work.


## Function to call main
if __name__ == '__main__':
  main()
  
