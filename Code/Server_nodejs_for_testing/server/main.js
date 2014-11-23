/*jslint node: true */
'use strict';
var express = require('express'),
    routes = require('./routes'),
    app = express();

app.use(express.bodyParser());

app.get('/', routes.default);
app.get('/connect', routes.connect);

app.get('/light', routes.light);
app.get('/light/:state', routes.lightStatus);

app.get('/music', routes.music);
app.get('/music/:state', routes.musicStatus);

app.get('/sensor', routes.sensor);
app.get('/sensor/:state', routes.sensorState);



app.use(function (req, res) {
    res.json({'ok': false, 'status': '404'});
});

module.exports = app;