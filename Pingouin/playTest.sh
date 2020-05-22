iterator=0
while [ $iterator -lt 100 ]
do
	java Pingouin >> res2.txt
	iterator=$(($iterator+1))
done
