import random

print('I have a secret number in 1 to 100')
num = random.randrange(1, 101)
count = 0
while True:
  numGuess = int(input('Guess a number (Enter 0 to quit): '))
  if numGuess == 0:
    print('The secret number is:', num)
    print('Better luck next time!')
    break
  elif numGuess > num:
    print('You guessed too high!')
    count += 1
  elif numGuess < num:
    print('You guessed too small!')
    count += 1
  elif numGuess == num:
    count += 1
    print('Congratulations you did it in', count, 'try')
    break