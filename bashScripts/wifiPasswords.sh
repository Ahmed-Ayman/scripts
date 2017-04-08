#!/usr/bin/bash
#this has been tested on Fedora.. you can edit it with your networking config files path!

echo "Welcome to Wifi passwords picker!";
echo "those are your list of wifi networks" ;
 #Fedora puts the networks config right this path!
NETWORKS_PATH="/etc/sysconfig/network-scripts";

#list the files in this path | display the network names which starts with keys-* | cut the second field..
KEYS="$(ls -l ${NETWORKS_PATH}/keys-* | sed 's/^.*\(keys.*\)/\1/g'|cut -d '-' -f 2)";
#array of network_names 
ARRAY_OF_NETWORKS=(${KEYS//$'\n'/ }); 

C=0; #counter

for i in "${ARRAY_OF_NETWORKS[@]}" 
       
	#loop to display the network names with a label 0 --> n
do
	echo [${C}] "--> " $i; 
	((C++)) # count
done
((C--)) 

echo "enter a value between 0 and {$C} to indicate your wanted network's label"

read network_label; #read a value .. which network..

if [ $network_label -gt $C ] 
	then
		echo 'something is Wrong!'
	else 
	
		NETWORK_NAME=${ARRAY_OF_NETWORKS[network_label]};  #getting the network that correspond the label (index).
		PASSWORD="$(sudo cat ${NETWORKS_PATH}/keys-${NETWORK_NAME}|cut -d '=' -f 2)"  #display the password under the root privilages
		echo  "${NETWORK_NAME}'s password is "$PASSWORD; 
fi


