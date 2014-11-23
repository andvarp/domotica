/*jslint node: true */
'use strict';


var state = {
    connected: true,
    light: true,
    music: true,
    sensor: 0
}

exports.default = function(req, res) {
        res.json({
            "state": state
        });
};

exports.connect = function(req, res) {
        res.json({
            "status": state.connected
        });
};

exports.light = function(req, res) {
    res.json({
            "light": state.light
        });
};

exports.lightStatus = function(req, res) {
    state.light = (req.params.state =='1'?true:false);
    res.json({
        "light":state.light
    });
};

exports.music = function(req, res) {
    var status = true;
    res.json({
            "music": state.music
        });
};

exports.musicStatus = function(req, res) {
    state.music = (req.params.state =='1'?true:false);
    res.json({
        "music":state.music
    });
};

exports.sensor = function(req, res) {
    res.json({
        "sensor": state.sensor
    });
};
exports.sensorState = function(req, res) {
    state.sensor = parseInt(req.params.state);
    res.json({
        "sensor": state.sensor
    });
};



function simulation(){
    var delay = 100, count = 0;
    function delayed1 () {
        count += 1;
        console.log(count);
        state.sensor=count;
        if (count > 30) {
            delay += 3;
        }
        if (count < 100) {
            setTimeout(delayed1, delay);
        }else{
            console.log(">>>");
            delay = 100;count = 0;
            setTimeout(delayed1, delay);
        }
    }
    delayed1();
}
simulation()










