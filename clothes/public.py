from flask import *
from database import*


public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('home.html')



@public.route('/agent_registration',methods=['post','get'])	
def agent_registration():
	if "agent" in request.form:
		c=request.form['fname']
		l=request.form['lname']
		p=request.form['place']
		
		ph=request.form['phone']
		e=request.form['email']
		u=request.form['uname']
		pa=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:

			flash('already exist')

		else:
			
			q="insert into login values(null,'%s','%s','pending')"%(u,pa)
			id=insert(q)
			q="insert into agent values(null,'%s','%s','%s','%s','%s','%s','1')"%(id,c,l,p,ph,e)
			insert(q)
			flash('successfully')
			return redirect(url_for('public.agent_registration'))

	return render_template('agent_registration.html')




@public.route('/login',methods=['post','get'])	
def login():
	if "login" in request.form:
		u=request.form['uname']
		pa=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			lid=session['login_id']
			if res[0]['usertype']=="admin":
				return redirect(url_for('admin.admin_home'))

			elif res[0]['usertype']=="agent":
				q="select * from agent where login_id='%s'"%(lid)
				res=select(q)
				if res:
					session['agent_id']=res[0]['agent_id']
					aid=session['agent_id']
				return redirect(url_for('agent.agent_home'))


		else:
			flash('invalid username and password')

	return render_template('login.html')