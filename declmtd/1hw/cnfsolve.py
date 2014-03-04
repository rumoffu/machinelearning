#!/usr/bin/env python
## This program runs all .enc files through DIMACS and zchaff and outputs the formula and the resulting answer

import os, subprocess

def main():
  for enc in sorted(os.listdir('.')):
    basefn, ext = os.path.splitext(enc)
    if enc.endswith('.enc'):# and (basefn + '.ans' not in os.listdir('.')):
      cmd = 'convertToDIMACS {enc} > {basefn}.cnf'.format(**locals())
      #print cmd
      subprocess.call(cmd, shell=True)
      cmd = 'zchaff {basefn}.cnf > {basefn}.output'.format(**locals())
      #print cmd
      subprocess.call(cmd, shell=True)
      cmd = 'readOutput {basefn}.output {basefn}.key > {basefn}.ans'.format(**locals())
      #print cmd
      subprocess.call(cmd, shell=True)

      cmd = 'cat {basefn}.enc'.format(**locals())
      #print cmd
      #subprocess.call(cmd, shell=True)
      cmd = 'cat {basefn}.ans | sort'.format(**locals())
      #print cmd
      #subprocess.call(cmd, shell=True)
      #print

## Function to call main
if __name__ == '__main__':
  main()
