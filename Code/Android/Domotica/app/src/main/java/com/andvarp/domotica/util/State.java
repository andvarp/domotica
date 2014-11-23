package com.andvarp.domotica.util;

/**
 * Created by andvarp on 11/20/14.
 */
public class State{
    Boolean connected;
    Boolean light;
    Boolean music;

    public State(Boolean connected, Boolean light, Boolean music, int sensor) {
        this.connected = connected;
        this.light = light;
        this.music = music;
        this.sensor = sensor;
    }

    public State() {
    }

    int sensor;

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getLight() {
        return light;
    }

    public Boolean toogleLight() {
        light = !light;
        return light;
    }

    public void setLight(Boolean light) {
        this.light = light;
    }

    public Boolean getMusic() {
        return music;
    }

    public Boolean toogleMusic() {
        music = !music;
        return music;
    }

    public void setMusic(Boolean music) {
        this.music = music;
    }

    public int getSensor() {
        return sensor;
    }

    public void setSensor(int sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "State{" +
                "connected=" + connected +
                ", light=" + light +
                ", music=" + music +
                ", sensor=" + sensor +
                '}';
    }
}
