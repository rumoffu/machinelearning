#!/usr/bin/env python
# Kyle Wong
# Declarative Methods HW2
# 14.3.14
# This solves Benchmark Problem 2 (which is hw question 5 and 6)

def main():
  initHeader() #print out the initial header
  fn = 'rcps.data'

  s1 = [] #section1 data
  s2 = [] #section2 data
  s4 = [] #section4 data
  with open(fn, 'r') as inp:
    sect = 0
    for li in inp.readlines():
      if 'section' in li:
        sect = int(li.strip()[7]) #get the number after section
      elif li.strip() == '': #ignore empty lines
        continue
      elif sect == 1:
        s1.append(li.strip().split(' ')) 
      elif sect == 2:
        s2.append(li.strip())
      elif sect == 4 and 'Zone.' in li: #we only need Zone. info
        s4.append(li.strip()[5:]) #ignore Zone. 
      elif sect == 5:
        break #we only need s1 and s2 and s4 for this part
  declVars(s1)
  declDurs(s1)
  declPrecedence(s2)
  declZones(s1, s4)
  declSolution(s1)

# Initializes the header for the ecl file
def initHeader():
  print '% Kyle Wong'
  print '% Declarative Methods HW2'
  print '% Problem 2'
  print ':- lib(ic).                     % include the standard interval constraint library'
  print ':- lib(branch_and_bound).       % include the branch and bound library for minimization'
  print ':- lib(ic_edge_finder).         % include the cumulative constraint library needed for resource constraints'
  print ''
  print 'solve(EndTime) :-'

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

# Declares the Zone constraints
def declZones(s1, s4):
  print '\t%declare Zones'     
  maxlist = []
  for li in s4:
    name, lim = li.split(' ')
    maxlist.append(lim)
  for i in range(len(s4)): #for each zone 0 to 13 (a to m) make a constraint
    varlist = []
    timelist = []
    uselist = []
    for li in s1: #go through all constraints
      name = li[0].split('.')[1] #periods not allowed
      hrs, mins = li[1].split(':') #cut time at the colon
      total = int(hrs)*60 + int(mins)
      if int(li[6+i]) > 0: #zone consideration, so add to the list
        varlist.append('S_{name}'.format(**locals()))
        timelist.append(total)
        uselist.append(int(li[6+i]))
    zonelim = maxlist[i]
    if varlist: # not empty
      s = str(varlist)
      stringvarlist = s.replace("'", "") #remove apostrophes/quotes
      print '\t\tcumulative({stringvarlist},{timelist},{uselist}, {zonelim}),'.format(**locals())
  print ''

# Declares the solution commands and output commands
def declSolution(s1):
  print '\t%declare solution commands'     
  print '\t\tEndTime #= max(TaskFinishTimes),'
  print '\t\tflatten([TaskStartTimes, TaskFinishTimes, EndTime], AllVars),'
  #too slow#print '\t\tminimize(labeling(AllVars), EndTime),'
  Select = 'input_order' #most constraints first #input order, first fail, smallest, largest, occurrence, and most constrained
  Choice = 'indomain_middle' #indomain, indomain_min, indomain_max, indomain_middle, indomain_reverse_min, indomain_reverse_max, indomain_median, indomain_split, indomain_reverse_split, indomain_random, indomain_interval
  Method = 'lds(0)' #complete, bbs(Steps:integer), lds(Disc:integer), credit(Credit:integer, Extra:integer or bbs(Steps:integer) or lds(Disc:integer)), dbs(Level:integer, Extra:integer or bbs(Steps:integer) or lds(Disc:integer)), sbds, gap_sbds, gap_sbdd
  OptionList = '[]' # backtrack(-N), node(++Call), nodes(++N)
  print '\t\tminimize(search(AllVars, 0, {Select}, {Choice}, {Method}, {OptionList}), EndTime),'.format(**locals())
  print ''
  for li in s1:
    name1 = li[0]
    name2 = li[0].split('.')[1] #periods not allowed
    print '\t\tprintf("{name1}: %d - %d %n", [S_{name2}, F_{name2}]),'.format(**locals())
  print '\tprintf("Done", []).'


if __name__ == '__main__':
  main()





