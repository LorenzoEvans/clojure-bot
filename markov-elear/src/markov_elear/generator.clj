(ns markov-elear.generator
  (:require clojure.java.io))

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

(defn text->word-chain [s]
  (let [words (clojure.string/split s #"[\s | \n]")
        word-transitions (partition-all 3 1 words)]
    (word-chain word-transitions)))

(defn walk-chain [prefix chain result]
  (let [suffixes (get chain prefix)]
    (if (empty? suffixes)
      result
      (let [suffix (first (shuffle suffixes))
            new-prefix [(last prefix) suffix]
            result-with-spaces (chain->text result)
            result-char-count (count result-with-spaces)
            suffix-char-count (inc (count suffix))
            new-result-char-count (+ result-char-count suffix-char-count)]
        (if (>= new-result-char-count)
          result
          (recur new-prefix chain (conj result suffix)))))))

(defn generate-text [start-phrase word-chain]
  (let [prefix (clojure.string/split start-phrase #" ")
        result-chain (walk-chain prefix word-chain prefix)
        result-text (chain->text result-chain)]
    result-text))

(defn process-file [fname]
  (text->word-chain
   (slurp (clojure.java.io/resource fname))))

(generate-text "And the" (process-file "qw.txt"))
