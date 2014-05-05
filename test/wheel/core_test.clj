(ns wheel.core-test
  (:require [clojure.test :refer :all]
            [wheel.core :refer :all]
            [wheel.sql :as sql]))

(def u "thisisatestuser")
(def p "thisisatestpassword")
(def wp "wrongpassword")
(def np "thisisanothertestpassword")

(deftest core-test
  (try (sql/migration)
       ;; perhaps the table already exists, ignore the error.
       (catch Exception e (println e)))

  (testing "create user"
    (sql/add-user u p)
    (is (sql/check u p)))

  (testing "change password"
    (sql/change-password u p np)
    (is (sql/check u np)))

  (testing "validate password"
    (is (not (sql/check u wp))))
  
  (sql/delete-user u))

