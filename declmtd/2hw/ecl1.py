#!/usr/bin/env python
# Kyle Wong
# Declarative Methods HW2
# 14.3.14
# This solves Benchmark Problem 1 (which is hw question 4)

def main():
  initHeader() #print out the initial header
  fn = 'rcps.data'

  s1 = [] #section1 data
  s2 = [] #section2 data
  with open(fn, 'r') as inp:
    sect = 0
    for li in inp.readlines():
      if 'section' in li:
        sect = int(li.strip()[7]) #get the number after section
      elif li.strip() == '': #ignore empty lines
        continue
      elif sect == 1:
        s1.append(li.split(' ')[0:2]) #only need first two for this problem)
      elif sect == 2:
        s2.append(li.strip())
      elif sect == 3:
        break #we only need s1 and s2 for this part
  declVars(s1)
  declDurs(s1)
  declPrecedence(s2)
  declSolution(s1)

# Initializes the header for the ecl file
def initHeader():
  print '% Kyle Wong'
  print '% Declarative Methods HW2'
  print '% Problem 1'
  print ':- lib(ic).                     % include the standard interval constraint library'
  print ':- lib(branch_and_bound).       % include the branch and bound library for minimization'
  print ':- lib(ic_edge_finder).         % include the cumulative constraint library needed for resource constraints'
  print ''
  print 'solve(X) :-'

# Declares the variables, 1 per line
def declVars(s1):
  print '\t%declare variables'
  print '\tTaskFinishTimes = ['
  for li in s1:
    name = li[0].split('.')[1] #periods not allowed
    print '\t\tF_{name},'.format(**locals())
  print '\tF_emptyvar],' #add emptyvar to solve trailing comma
  print '\tTaskStartTimes = ['
  for li in s1:
    name = li[0].split('.')[1] #periods not allowed
    print '\t\tS_{name},'.format(**locals())
  print '\tS_emptyvar],' #add emptyvar to solve trailing comma
  print ''
  print "\tTaskFinishTimes :: 0..600000," #2 digit -> max 99 hours + 60 minutes = 100 hours = 6000 minutes
  print "\tTaskStartTimes :: 0..600000,"
  print ''

# Declares the duration constraints
def declDurs(s1):
  print '\t%declare durations'
  for li in s1:
    name = li[0].split('.')[1] #periods not allowed
    hrs, mins = li[1].split(':') #cut at the colon
    total = int(hrs)*60 + int(mins)
    print '\t\tF_{name} - S_{name} #= {total},'.format(**locals())

# Declares the precedence constraints
def declPrecedence(s2):
  print '\t%declare precedence'
  for li in s2:
    first, second = li.split(' ')
    first = first.split('.')[1]
    second = second.split('.')[1]
    print '\t\tS_{second} #>= F_{first},'.format(**locals())

# Declares the solution commands and output commands
def declSolution(s1):
  print '\t%declare solution commands'     
  print '\t\tEndTime #= max(TaskFinishTimes),'
  print '\t\tflatten([TaskStartTimes, TaskFinishTimes, EndTime], AllVars),'
  print '\t\tminimize(labeling(AllVars), EndTime),'
  print ''
  for li in s1:
    name1 = li[0]
    name2 = li[0].split('.')[1] #periods not allowed
    print '\t\tprintf("{name1}: %d - %d %n", [S_{name2}, F_{name2}]),'.format(**locals())
  print '\tprintf("Done", []).'


if __name__ == '__main__':
  main()













