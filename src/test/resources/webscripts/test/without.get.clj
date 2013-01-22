;; NOTE: any ns form would be ignored

(use '[spring.surf.webscript :as ws])
(import spring.surf.webscript.WebScript)

(defn calc
  "Sample calculation"
  [{:keys [a b]}]
  (println (str "got " a " and " b "."))
  (+ (Integer/parseInt a) (Integer/parseInt b)))

(defrecord TestWebScript
  []
  
  WebScript
  (run
   [_ in out model]
   (.write out "streamed content")
   (ws/return  model {"clojureVal" "SUCCESS"})))

(TestWebScript.)
