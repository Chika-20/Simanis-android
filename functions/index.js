var functions = require('firebase-functions');
var admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

//fungsi notifikasi 1
exports.sendNotification = functions.database.ref('/Pasien/{userModelId}')
        .onWrite((change, context)=>{
            var eventSnapshot = change.after;

            var topic = "android";
            var payload = {
                data : {
                    vol_akhir: eventSnapshot.child("vol_akhir").val(),
                    ruangan: eventSnapshot.child("ruangan").val(),
                    bangsal: eventSnapshot.child("bangsal").val(),
                    body: "Segera ganti infus pasien"
                }
            };

            if (payload.data.vol_akhir <= 400){
            // Send a message to devices subscribed to the provided topic.
            return admin.messaging().sendToTopic(topic,payload)
                .then(function (response){
                    console.log("Successfully sent message:", response);
                })
                .catch(function(error){
                    console.log("Error sending message:",error);
                });
            }
        });