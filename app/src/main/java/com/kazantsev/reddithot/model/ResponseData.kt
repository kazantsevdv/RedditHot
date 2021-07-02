package com.kazantsev.reddithot.model

import com.google.gson.annotations.Expose

class ResponseData(
     @Expose val after: String?,
     @Expose  val children: List<DataPost>
     )