(ns :require markov-elear.generator)

; (defn foo
;   "I don't do a whole lot."
;   [x]
;   (println x "Hello, World!"))

(def example "And the Golden Grouse And The Pobble who.")

(def words (clojure.string/split example #" "))

(def word-transitions (partition-all 3 1 words))
