package main

import (
	"log"
	"github.com/gin-gonic/gin"
	"os/exec"
)

type Request struct {
	Text    string `form:"text" json:"text"`
}

func main() {
	route := gin.Default()
	route.GET("/text_to_material", textToMaterial)
	route.GET("/ping", pong)
	route.Run(":8080")
}

func pong(c *gin.Context) {
    c.JSON(200, gin.H{
        "message": "pong",
    })
}

func textToMaterial(c *gin.Context) {
    c.Header("Access-Control-Allow-Origin", "*")
    c.Header("Access-Control-Allow-Headers", "access-control-allow-origin, access-control-allow-headers")
	var req Request
	if c.BindQuery(&req) == nil {
		log.Println("====== Bind By Query String ======")
		log.Printf("Text: %s\n", req.Text)
	}
	/*
	if c.BindJSON(&req) == nil {
		log.Println("====== Bind By JSON ======")
		log.Println(req.Text)
	}
	*/
	out, err := exec.Command("./predict", req.Text).Output()
	if err != nil {
		panic(err.Error())
	}
	c.String(200, string(out))
}
