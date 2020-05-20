package com.example.realmdemo

import io.realm.DynamicRealm
import io.realm.RealmMigration
import io.realm.RealmObjectSchema
import io.realm.RealmSchema

class RealmMigrations : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val  schema : RealmSchema = realm.schema;

        if (oldVersion == 1L) {
            val  studentSchema : RealmObjectSchema? = schema.get("StudentModel");
            studentSchema!!.addField("rollNo", String::class.java)
        }
    }

}