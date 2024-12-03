Local run with Run Configurations (gradle)
JDK 11, Gradle

All connections properties are in BaseTest class,
but they better to be in environment variables to change them in CI/CD fast.

### Todo JSON structure
The only entity here is TODO  represented by a structure with the following three fields:
* `id` — an unsigned 64-bit identifier
* `text` - description of TODO - *field length missing*
* `completed` - whether the todo is completed or not

### `GET /todos`

Available query parameters:
* `offset` — how many TODOs should be skipped - *data type missing, eg length*
* `limit` - the maximum number of TODOs to be returned - *data type missing, eg length*

Covered only positive cases here (as long as full test coverage not required< as I understood from the description)
Of course there should be also negative cases for both parameters

### `POST /todos`

There could have been checks for corner cases with length of "text" field, but there is no length value in description.

### `PUT /todos/:id`

Possible cases for update - check each field limit within update operation. Can't check cause no limits described
Field id seems to be possible to update with existing id. Probably this is a bug

### `DELETE /todos/:id`

Might be cases without authorization, with bad password, without id

### `/ws`

Could not connect to this endpoint, so no tests for this written.