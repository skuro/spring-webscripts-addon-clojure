(ns test.alfresco)

(def model (var-get (ns-resolve 'spring.surf.webscript 'model)))

(assoc! model :clojureVal "SUCCESS")