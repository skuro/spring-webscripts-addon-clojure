(ns test.basic.script)

(use '[spring.surf.webscript :as ws])
(import spring.surf.webscript.WebScript)

(defrecord TestWebScript
  []
  
  WebScript
  (run [_ in out model]
       (ws/return model {"clojureVal" "SUCCESS"})))

(TestWebScript.)
