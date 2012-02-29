(ns adventure-in-core-logic.nani
  (:refer-clojure :exclude [reify inc ==])
  (:use [clojure.core.logic]))

(defrel room rtype)
(fact room 'kitchen)
(fact room 'office)
(fact room 'hall)
(fact room 'dining_room)
(fact room 'cellar)

(defrel location thing room)
(fact location 'desk 'office)
(fact location 'apple 'kitchen)
(fact location 'flashlight 'desk)
(fact location 'washing_machine 'cellar)
(fact location 'nani 'washing_machine)
(fact location 'broccoli 'kitchen)
(fact location 'crackers 'kitchen)
(fact location 'computer 'office)

(defrel door room1 room2)
(fact door 'office 'hall)
(fact door 'kitchen 'office)
(fact door 'hall 'dining_room)
(fact door 'kitchen 'cellar)
(fact door 'dining_room 'kitchen)

(defrel edible thing)
(fact edible 'apple)
(fact edible 'crackers)

(defrel tastes_yucky thing)
(fact tastes_yucky 'broccoli)

(defrel turned_off thing)
(fact turned_off 'flashlight)

;;(defrel here room)
;;(fact here 'kitchen)
(def here (atom 'kitchen))

(defn istrue [vfact var]
  (if (empty? (run 1 [q] (vfact var))) 'no 'yes))

(istrue room 'office)
(istrue room 'attic)

(run 1 [q] (location 'apple 'kitchen));;_.0 -> yes

(run* [q] (room q))

(run* [q] (fresh [Thing Place] (== q [Thing Place]) (location Thing Place)))

;;compound queries
(run* [q] (location q 'kitchen) (edible q))
;;(crackers apple)

(run* [q] (fresh [R T] (door 'kitchen R) (location T R) (== q [R T])))

;; Everything in kitchen
;;(doseq [i (run* [q] (location q 'kitchen))] (println i))


;;rules
(defn where_food [x y]
  (all (location x y)
       (edible x)))

(run* [q] (where_food q 'kitchen))

(run* [Thing] (where_food Thing 'dining_room))

(run 1 [q] (where_food 'apple 'kitchen));; yes/no

(run* [q] (fresh [Thing Room] (where_food Thing Room) (== q [Thing Room])))


(defn where_food [x y]
  (conde
    ((all (location x y)
          (edible x)))
    ((all (location x y)
          (tastes_yucky x)))))

(run* [q] (where_food q 'kitchen))


(defn connect [x y]
  (conde
    ((door x y))
    ((door y x))))

(run 1 [q] (connect 'kitchen 'office))
(run 1 [q] (connect 'office 'kitchen))

(run* [q] (fresh [X Y] (connect X Y) (== q [X Y])))

(defn list_things [Place]
  (run* [q] (location q Place)))

(defn list_connections [Place]
  (run* [q] (connect Place q)))

(defn look []
  (let [Place @here]
    (println "You are in the" Place)
    (println "You can see: ")
    (doseq [i (list_things Place)] (println "  " i))
    (println "You can go to: ")
    (doseq [i (list_connections Place)] (println "  " i))))

(defn look_in [where]
  (run* [q] (location q where)))

;;arithmetic -> this is a lisp dialect :D
;;(def some_arithmetic (+ 1 (/ 280 (* 7 4)))

;;Managing data
;;We are gonna use Clojure's Atoms for storing the current location: here

(defn can_go [Place]
  (if ((comp not empty?) (run* [q] (connect @here Place)))
    true
    (println "You can't get there form here.")))

;;true / nil
;;(can_go 'office)
;;(can_go 'hall)

;; Clojure solution (we use an atom insted of deleting the fact here and refreshing it)
(defn move [Place]
  (reset! here Place))

(defn goto [Place]
  (if (can_go Place)
    (move Place))
  (look))

;;(goto 'office)

(defn can_take [Thing]
  (if ((comp not empty?) (run* [q] (location Thing @here)))
    true
    (println "There is no" Thing "here.")))

(defrel have x)

(defn take_object [x]
  (retraction location x @here)
  (fact have x)
  (println "taken"))

;; take is reserved
(defn take_now [x]
  (if (can_take x)
    (take_object x)))

(defn put [x]
  (if ((comp not empty?) (run* [q] (have x)))
    (do (retraction have x) (fact location x @here) (println "Putting" x "down"))
    (println "You don't have" x)))

(defn inventory []
  (println "You have: ")
  (doseq [i (run* [q] (have q))] (println i)))

;; TODO: Excercise 4 5

(run* [q] (location 'flashlight 'office)) ;; ()

(fact location 'envelope 'desk)
(fact location 'stamp 'envelope)
(fact location 'key 'envelope)

(defn is_contained_in [T1 T2]
  (conde
    ((location T1 T2))
    ((fresh [X] (location X T2)
                (is_contained_in T1 X)))))
