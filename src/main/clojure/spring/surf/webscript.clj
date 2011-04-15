(ns spring.surf.webscript)

(defprotocol WebScript
  "Basic interface for a WebScript"
  (eval [this model] "Run the webscript and return the model"))

;(defn view-add!
;  "Adds new values to be used in view composition stage"
;  ([#^IPersistentMap view-map] (reset! *model* (merge @*model* view-map)))
;  ([k v] (view-add! {(keyword k) v})))
