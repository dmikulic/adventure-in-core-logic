# Adventure in Clojure core.logic

Inspired by Adventure in Prolog

Just a few examples are implemented.

## License

Copyright (C) 2012 Darko Mikulic

Distributed under the Eclipse Public License, the same as Clojure.


## Example run

```clj
user=> (load "adventure_in_core_logic/nani")
user=> (in-ns 'adventure-in-core-logic.nani)
```

```clj
adventure-in-core-logic.nani=> (look)
You are in the kitchen
You can see: 
   crackers
   apple
   broccoli
You can go to: 
   cellar
   dining_room
   office
nil
adventure-in-core-logic.nani=> (take_now 'apple)
taken
nil
adventure-in-core-logic.nani=> (inventory)
You have: 
apple
nil
adventure-in-core-logic.nani=> (goto 'dining_room)
You are in the dining_room
You can see: 
You can go to: 
   kitchen
   hall
nil
adventure-in-core-logic.nani=> (goto 'hall)
You are in the hall
You can see: 
You can go to: 
   dining_room
   office
nil
adventure-in-core-logic.nani=> (goto 'office)
You are in the office
You can see: 
   desk
   computer
You can go to: 
   hall
   kitchen
nil
adventure-in-core-logic.nani=> (take_now 'computer)
taken
nil
adventure-in-core-logic.nani=> (inventory)
You have: 
computer
apple
nil
adventure-in-core-logic.nani=> (put 'apple)
Putting apple down
nil
adventure-in-core-logic.nani=> (inventory)
You have: 
computer
nil
adventure-in-core-logic.nani=> (goto 'kitchen)
You are in the kitchen
You can see: 
   crackers
   broccoli
You can go to: 
   cellar
   dining_room
   office
nil
adventure-in-core-logic.nani=> (run* [q] (is_contained_in q 'desk))
(flashlight envelope key stamp)
```