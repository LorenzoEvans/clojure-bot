(ns markov-elear.generator-test
  (:require [clojure.test :refer :all]
            [markov-elear.generator :refer :all]))

(deftest test-word-chain
  (testing "it produces a chain of the possible two step transitions between suffixes and prefixes"
    (let [example '(("And" "the" "Golden")
                    ("the" "Golden" "Grouse")
                    ("And" "the" "Pobble")
                    ("the" "Pobble" "who"))]
      (is (= {["the" "Pobble"] #{"who"}
              ["the" "Golden"] #{"Grouse"}
              ["And" "the"] #{"Pobble" "Golden"}}
             (word-chain example))))))

(deftest test-text->word-chain
  (testing "string with spaces and new-lines"
    (let [example "And the Golden Grouse\nAnd the Pobble Who"]
      (is (= {["who" nil] #{}
              ["Pobble" "who"] #{}
              ["the" "Pobble"] #{"who"}
              ["Grouse" "And"] #{"the"}
              ["Golden" "Grouse"] #{"And"}
              ["the" "Golden"] #{"Grouse"}
              ["And" "the"] #{"Pobble" "Golden"}}
             (test->word-chain example))))))

(defn text->word-chain [s]
  (let [words (clojure.string/split s #"[\s | \n]")
        word-transitions (partition-all 3 1 words)]
    (word-chain word-transitions)))
