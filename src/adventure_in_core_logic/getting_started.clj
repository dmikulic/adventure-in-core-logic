(ns adventure-in-core-logic.getting_started
  (:refer-clojure :exclude [reify inc ==])
  (:use [clojure.core.logic]))


(defn istrue [vfact var]
  (if (empty? (run 1 [q] (vfact var))) 'no 'yes))


(defrel person x)

(defn mortal [x] (person x))

(fact person 'Socrates)
(fact person 'Plato)

;;(run* [q] (mortal 'Socrates))
(istrue mortal 'Socrates)

(run 1 [q] (mortal q))

;;(println "hello world")

(fact person 'Zeno)
(fact person 'Aristotle)

(defn mortal-report []
  (println "Known mortals are:")
  (doseq [i (run* [q] (mortal q))] (println i)))

(defrel customer name city credit)
(fact customer "John Jones" 'Boston 'good_credit)
(fact customer "Sally Smith" 'Chicago 'good_credit)


(defrel window name ux uy lx ly)
(fact window 'main 2 2 20 72)
(fact window 'errors 15 40 20 78)

(defrel disease name prop)
(fact disease 'plague 'infectious)
