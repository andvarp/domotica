#include <SPI.h>
#include <Ethernet.h>
#include "pitches.h"

//Server
byte mac[] = {0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x01 };
EthernetServer server(80);
String readString;

//Domotics variables
const int sensorPin = 0;
const int ledPin = 6;
const int tonePin = 8;
int melody[] = {NOTE_C4, NOTE_G3,NOTE_G3, NOTE_A3, NOTE_G3,0, NOTE_B3, NOTE_C4};
int noteDurations[] = {4, 8, 8, 4,4,4,4,4 };

//State Variables
boolean connectedState = true;
boolean lightState = false;
boolean musicState = false;
int sensorState = 0;
String connectedStateSrt = "";
String lightStateStr = "";
String musicStateStr = "";

//
boolean musicRuning = false;

void setup() {
  Serial.begin(9600);
  
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    for(;;)
      ;
  }

  Serial.print("My IP address: ");
  for (byte thisByte = 0; thisByte < 4; thisByte++) {
    // print the value of each byte of the IP address:
    Serial.print(Ethernet.localIP()[thisByte], DEC);
    Serial.print("."); 
  }
  Serial.println();
  
  pinMode(ledPin, OUTPUT);
}

void loop() {
   // Create a client connection
  EthernetClient client = server.available();
  if (client) {
    while (client.connected()) {   
      if (client.available()) {
        char c = client.read();
        //read char by char HTTP request
        if (readString.length() < 100) {
          //store characters to string
          readString += c;
          //Serial.print(c);
         }

         //if HTTP request has ended
         if (c == '\n') {          
           Serial.println(readString); //print to serial monitor for debuging
           
            //playTone();
            
            client.println("HTTP/1.1 200 OK");
            client.println("Content-Type: application/json");
            client.println();
           
           if (readString.indexOf("/connect") >0){
             updateStates();
             String _connect = "{\"connected\":" + connectedStateSrt + ",\"light\": " + lightStateStr + ",\"music\": " + musicStateStr + ",\"sensor\": " + sensorState + "}";
             client.println(_connect);
           }
           
           if (readString.indexOf("/sensor") >0){
             updateStates();
             String _sensor = "{\"sensor\": " + String(sensorState) + "}";
             client.println(_sensor);
           }
           
           if (readString.indexOf("/light/0") >0){
             lightState = false;
             analogWrite(ledPin, LOW);            
             Serial.println("Sending: " + String(lightState));
             updateStates();
             String _light = "{\"light\": " + lightStateStr + "}";
             client.println(_light);
           }
           
           if (readString.indexOf("/light/1") >0){
             lightState = true;
             analogWrite(ledPin, HIGH);
             Serial.println("Sending: " + String(lightState));
             updateStates();
             String _light = "{\"light\": " + lightStateStr + "}";
             client.println(_light);
           }
           
           if (readString.indexOf("/music/0") >0){
             musicState = false;
             updateStates();
             String _music = "{\"music\": " + lightStateStr + "}";
             client.println(_music);
           }
           
           if (readString.indexOf("/music/1") >0){
             musicState = true;
             updateStates();
             String _music = "{\"music\": " + lightStateStr + "}";
             client.println(_music);
           }
           
           
           
           

     
           delay(1);
           client.stop();
           if (readString.indexOf("/music/1") >0){
//             if(!musicRuning){
//               playTone();
//             }
           }
           readString="";  
           
         }
       }
    }
}
}//loop
void updateStates(){
  if(connectedState){connectedStateSrt = "true";}else{connectedStateSrt = "false";}
  if(lightState){lightStateStr = "true";}else{lightStateStr = "false";}
  if(musicState){musicStateStr = "true";}else{musicStateStr = "false";}
  sensorState = 100-(map(analogRead(sensorPin), 0, 1023, 0, 100));
}

//void playTone(){
//  if(musicState){
//    musicRuning = true;
//    for (int thisNote = 0; thisNote < 8; thisNote++) {
//      int noteDuration = 1000/noteDurations[thisNote];
//      tone(tonePin, melody[thisNote],noteDuration);
//      int pauseBetweenNotes = noteDuration * 1.30;
//      delay(pauseBetweenNotes);
//      noTone(tonePin);
//    }
//    musicRuning = false;
//    musicState = false;
//  }
//}
