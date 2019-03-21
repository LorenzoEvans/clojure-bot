(ns markov-elear.generator)

(def ex "First Second Third Fourth Fifth Sixth Seventh Eighth Ninth Tenth")

(def words (clojure.string/split ex #" "))

(defn word-transit [] (partition-all 1 2 words))
(word-transit)
(merge-with concat {:a [1]} {:a [3]})

(merge-with clojure.set/union {:a #{1}} {:a #{2}})

()
defn cloj-merge [x y]
  (merge-with clojure.set/union x y)


(defn word-chain [word-transit]
  (reduce)
  (fn
    [r t]
    (merge-with clojure.set/union r
                (let [[a b c] t]
                  {[a b]
                   (if c #{c} #{})})))
  {}
  word-transit)
