from flask import * 
from database import*



admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():

	return render_template('admin_home.html')


@admin.route('/viewagent')
def viewagent():
	data={}
	q="SELECT * FROM  agent inner join login using (login_id)"
	res=select(q)
	data['carbook']=res


	if "action" in request.args:
		action=request.args['action']
		aid=request.args['aid']


	else:
		action=None


	if action=='accept':
		q="update login set usertype='agent' where login_id='%s'"%(aid)
		update(q)
		return redirect(url_for('admin.viewagent'))


	if action=='reject':
		q="update login set usertype='Reject' where login_id='%s'"%(aid)
		update(q)
		return redirect(url_for('admin.viewagent'))


	return render_template('viewagent.html',data=data)

@admin.route('/viewclothes')
def viewclothes():
	data={}
	q="SELECT * FROM `product` INNER JOIN `agent` USING (`agent_id`) "
	res=select(q)
	data['pro']=res


	return render_template('viewclothes.html',data=data)


@admin.route('/viewbookings')
def viewbookings():
	data={}
	q="SELECT *,concat(booking.status) as bsatatus FROM `booking` INNER JOIN `product` USING (`product_id`) inner join customer using (customer_id) "
	res=select(q)
	data['cust']=res


	return render_template('viewbookings.html',data=data)

@admin.route('/viewcomplaints')
def viewcomplaints():
	data={}
	q="SELECT * FROM `complaint` INNER JOIN `customer` USING (`customer_id`)  "
	res=select(q)
	data['comp']=res


	return render_template('viewcomplaints.html',data=data)

@admin.route('/admin_sendreply',methods=['post','get'])
def admin_sendreply():
	data={}

	if "sendreply" in request.form:
		reply=request.form['reply']
		cid=request.args['id']
		q="update complaint set reply='%s' where complaint_id='%s'"%(reply,cid)
		update(q)
		return redirect(url_for('admin.viewcomplaints'))

	return render_template('admin_sendreply.html',data=data)



