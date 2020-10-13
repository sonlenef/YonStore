package tech.leson.yonstore.data

import tech.leson.yonstore.data.local.db.DbHelper
import tech.leson.yonstore.data.local.prefs.PreferencesHelper
import tech.leson.yonstore.data.remote.ApiHelper

interface DataManager : ApiHelper, PreferencesHelper, DbHelper {
}
