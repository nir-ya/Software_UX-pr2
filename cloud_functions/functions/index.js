// in node.js, modules are loaded into variables using the "require" function
const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

db = admin.firestore();


// cloud functions are added to the exports object to be deployed

/**
 * update order price on mana change
 */
exports.updatePriceOnManaChange = functions.firestore.document("OpenOrders/{orderId}/Manot/{manaId}").onUpdate((change, context) => {
    var manaAfterPrice = change.after.data().price;
    var manaBeforePrice = change.before.data().price;
    var orderRef = db.collection('OpenOrders').doc(context.params.orderId);
    return db.runTransaction(transaction => {
        return transaction.get(orderRef).then(orderDoc => {
            var newPrice = orderDoc.data().price + manaAfterPrice - manaBeforePrice;
            return transaction.update(orderRef, {
                price: newPrice
            });
        });
    });

});

/**
 * update order price on mana creation
 */
exports.updatePriceOnManaCreate = functions.firestore.document("OpenOrders/{orderId}/Manot/{manaId}").onCreate((snapshot, context) => {
    var manaPrice = snapshot.data().price;
    var orderRef = db.collection('OpenOrders').doc(context.params.orderId);

    return db.runTransaction(transaction => {
        return transaction.get(orderRef).then(orderDoc => {
            //var timestamp = orderDoc.data().timestamp;

            var newPrice = orderDoc.data().price + manaPrice;



            return transaction.update(orderRef, {
                price: newPrice
            });
        });
    });

});

exports.updateTimestampOnManaCreation = functions.firestore.document("OpenOrders/{orderId}/Manot/{manaId}").onCreate((snapshot, context) => {
    var orderRef = db.collection('OpenOrders').doc(context.params.orderId);

    return db.runTransaction(transaction => {
        return transaction.get(orderRef).then(orderDoc => {
            var timestamp = orderDoc.data().timestamp;
            var manaRef = db.collection('OpenOrders').doc(context.params.orderId).collection("Manot").doc(context.params.manaId)

            return transaction.update(manaRef, {
                timestamp: timestamp
            });
        });
    });

});

exports.updateManaStatusOnOrderStatusChange = functions.firestore.document("OpenOrders/{orderId}").onUpdate((change, context) => {
    var orderBeforeStatus = change.before.data().status;
    var orderAfterStatus = change.after.data().status;
    var orderId = context.params.orderId;
    if ((orderBeforeStatus === "open") && (orderAfterStatus === "closed" || orderAfterStatus === "canceled")) {
        return admin.firestore().collection('/OpenOrders/' + orderId + '/Manot')
            .get().then(snapshot => {
                const promises = [];
                snapshot.forEach(doc => {
                    promises.push(doc.ref.update({
                        status: orderAfterStatus
                    }));
                });
                return Promise.all(promises)
            })
            .catch(error => {
                console.log(error);
                return null;
            });
    }




});


/**
 * update order price on mana deletion
 */
exports.updatePriceOnManaDelete = functions.firestore.document("OpenOrders/{orderId}/Manot/{manaId}").onDelete((snapshot, context) => {
    var manaPrice = snapshot.data().price;
    var orderRef = db.collection('OpenOrders').doc(context.params.orderId);
    return db.runTransaction(transaction => {
        return transaction.get(orderRef).then(orderDoc => {
            var newPrice = orderDoc.data().price - manaPrice;
            if (newPrice === 0) {
                return transaction.delete(orderRef);
            }
            else {
                return transaction.update(orderRef, {
                    price: newPrice
                });
            }

        });
    });

});