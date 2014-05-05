(ns wheel.sql
  (:require [clojure.java.jdbc :as sql]
            [clojure.edn :as edn]
            [wheel.core :as wc]))

(defn- read-edn [f]
  (with-open [infile (java.io.PushbackReader. (clojure.java.io/reader f))]
    (edn/read infile)))

(def config (read-edn "config.edn"))

(defn migration
  "Creates the user database according to the schema defined in the config file."
  []
  (sql/with-db-connection [db-con (config :dbspec)]
    (sql/db-do-commands db-con
                        (apply (partial sql/create-table-ddl
                                        (config :user-table))
                               (config :schema)))))

(defn insert-user
  "Inserts a user record into the database. The user map must contain an id and a key."
  [{:keys [id key] :as user-map}]
  {:pre [(and id key)]}
  (sql/with-db-transaction [t (config :dbspec)]
    (sql/insert! t (config :user-table) user-map)))

(defn query-user
  "Retrieves a user record from the database."
  [id]
    (sql/with-db-transaction [t (config :dbspec)]
      (first (sql/query t [(str "select * from " (config :user-table) " where id=?") id]))))

(defn update-key
  "Replaces the key for the user identified by id."
  [id key]
  (sql/with-db-transaction [t (config :dbspec)]
    (sql/update! t
                 (config :user-table)
                 {:key key}
                 ["id=?" id])))

(defn delete-user
  "Deletes the database record for id."
  [id]
  (sql/with-db-transaction [t (config :dbspec)]
    (sql/delete! t
                 (config :user-table)
                 ["id=?" id])))

(defn add-user
  "Creates a database record for the given id and password. user-map must be
   consistent with the user table schema supplied in the config file."
  [id password & user-map]
  (wc/add-user id password insert-user user-map))

(defn check
  "Verifies that the supplied password is valid."
  [id password]
  (wc/check id password query-user))

(defn set-password
  "Sets the password for id."
  [id password]
  (wc/set-password id password update-key))

(defn change-password
  "Changes the password to new-pass, if old-pass is the valid password for id."
  [id old-pass new-pass]
  (wc/change-password id old-pass new-pass query-user update-key))

