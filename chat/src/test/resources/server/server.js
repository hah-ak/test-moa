const express = require('express')
const app = express();
const http = require('http').createServer(app);
const path = require('path')

app.use(express.static(path.join(__dirname, 'public')))
http.listen(3100,() => {})
