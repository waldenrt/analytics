import * as express from 'express'
import * as multer from 'multer'
import * as cors from 'cors'
import * as fs from 'fs'
import * as path from 'path'
import * as WebHDFS from 'webhdfs'

var hdfs = WebHDFS.createClient({
user: 'admin',
host: '10.4.3.26',
port: 14000,
path: '/webhdfs/v1'
});

// setup
const UPLOAD_PATH = 'uploads';
const upload = multer({ dest: `${UPLOAD_PATH}/` }); // multer configuration

// app
const app = express();
app.use(cors());

app.post('/file_upload', upload.single('file'), async (req, res) => {
    try {
        console.log(req.file.filename);
	console.log(req.file.path);

	var file = req.file.path;

	var localFileStream = fs.createReadStream(file);
	var remoteFileStream = hdfs.createWriteStream('/user/admin/' + req.file.originalname);
 
	localFileStream.pipe(remoteFileStream);
 
	remoteFileStream.on('error', function onError (err) {
  	   // Do something with the error
	   console.log("it failed");
	   console.log(err);
	   res.sendStatus(400);
	});
 
	remoteFileStream.on('finish', function onFinish () {
  	// Upload is done
	   console.log("it is done!"); 
	   res.send({success:"true",fileName:req.file.originalname});
	});
    } catch (err) {
        res.sendStatus(400);
    }
})

app.get('/hdfsFileList/:client', async (req, res) => {
	try {		
		var client = req.params.client
		hdfs.readdir('/user/admin', function(err,files) {
		if (err==null) {
			var filteredList = files.filter(function (el) {
				return (el.type === "FILE");
			});
			var fileName = filteredList.map(function(f) { return f.pathSuffix });
			console.log(fileName);
			res.send({success:"true", fileNames: fileName});
		} else {
			res.send({success:"false", errMsg: err})
		}
		})
	}
	catch (err) {
		res.sendStatus(400);
	}
})

app.listen(3000, function () {
    console.log('listening on port 3000!');
});
