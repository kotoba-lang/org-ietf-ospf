#!/usr/bin/env nbb
;; Run the suite on the ClojureScript side.
;;
;; Not a formality: `ospf.bytes` exists specifically because JavaScript's
;; bitwise operators are 32-bit and signed where the JVM's are 64-bit, and
;; `ospf.checksum` shifts and masks throughout. A codec that only round-trips
;; on `clj -M:test` has only proven itself on the platform least likely to
;; expose that class of bug.
;;
;;   nbb --classpath "$(clojure -A:cljs -Spath)" scripts/verify-cljs.cljs
(ns verify-cljs
  (:require [clojure.test :as t]
            [ospf.core-test]))

(defmethod t/report [:cljs.test/default :end-run-tests] [m]
  (println)
  (if (t/successful? m)
    (println "all checks passed on the ClojureScript path")
    (do (println "FAILED on the ClojureScript path")
        (js/process.exit 1))))

(t/run-tests 'ospf.core-test)
