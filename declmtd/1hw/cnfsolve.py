#!/usr/bin/env python
import os, subprocess

def main():
  for enc in os.listdir('.'):
    basefn, ext = os.path.splitext(enc)
    if enc.endswith('.enc') and (basefn + '.ans' not in os.listdir('.')):
      cmd = 'convertToDIMACS {enc} > {basefn}.cnf'.format(enc=enc, basefn=basefn)
      #print cmd
      subprocess.call(cmd, shell=True)
      cmd = 'zchaff {basefn}.cnf > {basefn}.output'.format(basefn=basefn)
      #print cmd
      subprocess.call(cmd, shell=True)
      cmd = 'readOutput {basefn}.output {basefn}.key > {basefn}.ans'.format(basefn=basefn)
      #print cmd
      subprocess.call(cmd, shell=True)
      cmd = 'cat {basefn}.ans'.format(basefn=basefn)
      #print cmd
      subprocess.call(cmd, shell=True)

## Function to call main
if __name__ == '__main__':
  main()
