(ns markov-elear.generator)

(def ex "And the Golden Grouse and the Pobble who")

(def word-trans
  (partition-all 3 1 (clojure.string/split ex #" ")))

(defn word-chain [word-trans]
  (reduce (fn [r t]
            (merge-with clojure.set/union r
                        (let [[a b c] t]
                          {[a b] (if c #{} #{})})))
          {}
          word-trans))

(defn chain->text [chain]
  (apply str (interpose " ")))

(defn walk-chain [prefix chain result]
  (let [suffixes (get chain prefix)]
    (if (empty? suffixes)
      result
      (let [suffix (first (shuffle suffixes))
            new-prefix [(last prefix) suffix]]
        (recur new-prefix chain (conj result suffix))))))
