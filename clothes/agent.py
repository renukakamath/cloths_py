from flask import * 
from database import*
import uuid



agent=Blueprint('agent',__name__)

@agent.route('/agent_home')
def agent_home():

	return render_template('agent_home.html')



@agent.route('/agent_viewproduct')
def agent_viewproduct():
	data={}
	q="SELECT * FROM `request` INNER JOIN `agent` USING (`agent_id`) inner join customer using (customer_id) "
	res=select(q)
	data['cust']=res


	if "action" in request.args:
		action=request.args['action']
		cid=request.args['cid']

	else:
		action=None

	if action=='sanitised':
		q="update request set status='sanitised' where customer_id='%s'"%(cid)
		update(q)
		return redirect(url_for('agent.agent_viewproduct'))
		






	return render_template('agent_viewproduct.html',data=data)



@agent.route('/agent_buy',methods=['post','get'])
def agent_buy():
	data={}
	if "buy" in request.form:
		cid=request.args['cid']
		amount=request.form['amount']
		q="insert into wallet values(null,'%s','%s',curdate())"%(cid,amount)
		insert(q)

		return redirect(url_for('agent.agent_viewproduct'))
	

	return render_template('agent_buy.html',data=data)





@agent.route('/addsanitiseproduct',methods=['post','get'])
def addsanitiseproduct():

	data={}
	q="select * from product inner join agent using (agent_id)"
	res=select(q)
	data['product']=res
	if "add" in request.form:
		cid=request.form['product']
		img=request.files['img']
		aid=session['agent_id']
		path="static/image"+str(uuid.uuid4())+img.filename
		img.save(path)
		amt=request.form['amt']
		q="insert into product values(null,'%s','%s','%s','pending','%s')"%(aid,cid,path,amt)
		insert(q)
		return redirect(url_for('agent.addsanitiseproduct'))
	

	return render_template('addsanitiseproduct.html',data=data)



@agent.route('/agent_viewbookings')
def agent_viewbookings():
	data={}
	q="SELECT *,concat(booking.status) as bsatatus FROM `booking` INNER JOIN `product` USING (`product_id`) inner join customer using (customer_id) "
	res=select(q)
	data['cust']=res



	if "action" in request.args:
		action=request.args['action']
		did=request.args['did']
	else:
		action=None

		
	if action=='deliver':
		q="update booking set status='deliver' where booking_id='%s'"%(did)
		update(q)
		return redirect(url_for('agent.agent_viewbookings'))


	return render_template('agent_viewbookings.html',data=data)



@agent.route('/agent_viewpayment')
def agent_viewpayment():
	data={}
	q="SELECT * FROM `payment` INNER JOIN `booking` USING (`booking_id`) inner join product using (product_id) "
	res=select(q)
	data['idea']=res


	return render_template('agent_viewpayment.html',data=data)