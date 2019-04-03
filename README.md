# Phone-Usage data gathering application
Android application that gathers data about the amount of time spent on the mobile device.
Additionally, application displays surveys that the user has to answer.
All data is synced with the [Rails Server](https://github.com/C0mpy/phone-usage-server)

### Installing
1. clone this repo and locate your terminal to phone-usage-app dir
	```
	$ git clone https://github.com/C0mpy/phone-usage-app
	$ cd ./phone-usage-app
	```
2. build the .apk file
	```$ ./gradlew assembleDebug```
3. copy: /phone-usage-app/app/build/outputs/apk/debug/app-debug.apk file to your mobile device
4. install app-debug.apk file on your mobile device

## Acknowledgments
* Hat tip to [igordejanovic](https://github.com/igordejanovic) for reviewing the code