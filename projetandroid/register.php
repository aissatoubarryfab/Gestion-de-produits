<?php

$db =new PDO("mysql:host=localhost;dbname=projetandroid","root","");
$results["error"]=false;
$results["message"]=[];
if(isset($_POST)){
	if(!empty($_POST['nom'])&& !empty($_POST['prenom']) && !empty($_POST['email']) && !empty($_POST['password'])){
	 $nom=$_POST['nom'];
	 $prenom=$_POST['prenom'];
	 $email=$_POST['email'];
	 $password=$_POST['password'];

	 if($results["error"]==false){
	 	$sql=$db->prepare("INSERT INTO users(nom,prenom,email,password)VALUES (:nom,:prenom,:email,:password");
	 	$sql->execute([":nom"=>$nom,":prenom"=>$prenom,":email"=>$email,":password"=>$password]);
	 	if(!$sql){
	 		$results["error"]=true;
	 		$results["message"]="erreur lors de l'inscription";
	 		 echo json_encode($results);
	 	}
	 }
	}
	else{
		$results["error"]=true;
		$results["message"]="Veuillez remplir tous les champs";
		 // echoing JSON response
         echo json_encode($results);
	}
}

?>