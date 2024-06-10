from flask import * 
from database import* 
import uuid

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	u=request.args['username']
	p=request.args['password']
	q1="select * from login where username='%s' and `password`='%s'"%(u,p)
	print(q1)
	res=select(q1)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)

@api.route('/userregister')
def userregister():
	data={}
	f=request.args['fname']
	l=request.args['lname']
	
	pl=request.args['place']
	
	ph=request.args['phone']
	e=request.args['email']
	u=request.args['username']
	p=request.args['password']

	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(NULL,'%s','%s','user')"%(u,p)
		lid=insert(q)
		r="insert into customer values(NULL,'%s','%s','%s','%s','%s','%s')"%(lid,f,l,pl,ph,e)
		insert(r)
		data['status']="success"
	return str(data)



@api.route('/viewagentspinner')
def viewagentspinner():
	data={}
	
	q="SELECT * FROM  `agent` "
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewagentspinner"
	return str(data)




@api.route('/Addproduct',methods=['post','get'])
def Addproduct():
	data={}
	lid=request.form['lid']
	aid=request.form['aid']
	req=request.form['request']
	details=request.form['details']
	i=request.files['image']
	path="static/image"+str(uuid.uuid4())+i.filename
	i.save(path)

	q="insert into request values(null,(select customer_id from customer where login_id='%s'),'%s','%s','%s','pending','%s')"%(lid,req,details,path,aid)
	insert(q)
	print(q)
	
	data['status']="success"
	data['method']='Addproduct'
	return str(data)

@api.route('/Viewwallet')
def Viewwallet():
	data={}
	log_id=request.args['log_id']
	
	q="SELECT * FROM  `wallet` where customer_id=(select customer_id from customer where login_id='%s') "%(log_id)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewwallet"
	return str(data)


@api.route('/Viewproduct')
def Viewproduct():
	data={}
	
	
	q="SELECT * FROM  `product` inner join agent using (agent_id)"
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewproduct"
	return str(data)


@api.route('/Buynow')
def Buynow():
	data={}
	login_id=request.args['log_id']
	pid=request.args['pid']
	amt=request.args['amt']

	q="insert into booking values(null,'%s',(select customer_id from customer where login_id='%s'),'%s',curdate(),'Booked')"%(pid,login_id,amt)
	insert(q)
	print(q)


	
	data['status']="success"
	data['method']='Buynow'
	return str(data)



@api.route('/viewbookings')
def viewbookings():
	data={}
	
	
	q="SELECT * FROM  `booking` inner join product using (product_id) inner join customer using (customer_id)"
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewbookings"
	return str(data)


@api.route('/Makepayments')
def Makepayments():
	data={}
	# login_id=request.args['log_id']
	bid=request.args['bid']
	amt=request.args['amount']

	q="insert into payment values(null,'%s','%s',curdate())"%(bid,amt)
	insert(q)
	q="update booking set status='Paid' where booking_id='%s'"%(bid)
	update(q)
	print(q)


	
	data['status']="success"
	data['method']='Makepayments'
	return str(data)


@api.route('/Makewallet')	
def Makewallet():
	data={}
	
	lid=request.args['login_id']
	bid=request.args['bid']

	q="select * from wallet where customer_id=(select customer_id from login where login_id='%s')"%(lid)
	res=select(q)

	amt=res[0]['amount']
	
	q="update wallet set amount=amount-'%s' where customer_id=(select customer_id from login where login_id='%s')"%(amt,lid)
	update(q)
	q="update booking set status='Paid' where booking_id='%s' "%(bid)
	update(q)
	data['status']='success'
	return str(data)

