(ns spring.surf.webscript)

(def *model* 
     (atom {}))

(defn view-add!
  "Adds new values to be used in view composition stage"
  ([#^IPersistentMap view-map] (reset! *model* (merge @*model* view-map)))
  ([k v] (view-add! {(keyword k) v})))