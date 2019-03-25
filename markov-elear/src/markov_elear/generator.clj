(ns markov-elear.generator)

(def ex "And the Golden Grouse and the Pobble who")

(def word-trans
  (partition-all 3 1(clojure.string/split ex #" ")))

(defn word-chain [word-trans]
  (reduce (fn [r t]
              (merge-with clojure.set/union r
                            (let [[a b c] t]
                                {[a b] (if c #{} #{})})))
    {}
    word-trans))
