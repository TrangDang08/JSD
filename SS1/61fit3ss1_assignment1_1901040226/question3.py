def MinGuess(start, end):
  numGuess = (start + end)//2
  return numGuess

print('Please pick a random number in 1 to 100 and keep it secret. I will try to guess your number')
start = 1
end = 100
count = 1
while True:
  numGuess = MinGuess(start, end)
  print('Is', numGuess, 'your number?')
  ans = input('Enter c if it is correct. Enter h if my guessed number is bigger than you. Otherwise enter l ')
  if ans == 'h':
    end = numGuess
    count += 1
  elif ans == 'l':
    start = numGuess
    count += 1
  elif ans == 'c':
    print('I did it in', count, 'try')
    break