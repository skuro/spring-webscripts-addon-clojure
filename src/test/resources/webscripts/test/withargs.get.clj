;; NOTE: any ns form would be ignored

(use '[spring.surf.webscript :as ws])

(defn calc
  "Sample calculation"
  [{:keys [a b]}]
  (println (str "got " a " and " b "."))
  (+ (Integer/parseInt a) (Integer/parseInt b)))

(defrecord TestWebScript
  []
  
  ws/WebScript
  (run
   [_ model]
   (ws/return model {:result (calc (merge (ws/template-args model) (ws/args model)))})))

(TestWebScript.)
